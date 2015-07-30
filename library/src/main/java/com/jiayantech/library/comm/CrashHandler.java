package com.jiayantech.library.comm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;

import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.utils.FileUtil;
import com.jiayantech.library.utils.TimeUtil;

/**
 * @author Stay 在Application中统一捕获异常，保存到文件中下次再打开时上传
 */
public class CrashHandler implements UncaughtExceptionHandler {
    /**
     * 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
     */
    public static final boolean DEBUG = true;
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;

    /** 程序的Context对象 */
    // private Context mContext;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        // mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex, true) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else { // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @return true代表处理该异常，不再向上抛异常，
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
     * 简单来说就是true不会弹出那个错误提示框，false就会弹出
     */
    public static boolean handleException(final Throwable ex, final boolean isCrash) {
        if (ex == null) {
            return false;
        }
        if (!isCrash) {
            ex.printStackTrace();
            return false;
        }

        // NoticeUtil.cancelAll();
        // final String msg = ex.getLocalizedMessage();
        // final StackTraceElement[] stack = ex.getStackTrace();
        // final String message = ex.toString();

        // 使用Toast来显示异常信息
        new Thread("handleExceptionThread") {
            @Override
            public void run() {
                Looper.prepare();
                String message = toStackTraceString(ex);
                BaseApplication.getContext().onCrash(ex, message);
                // 可以只创建一个文件，以后全部往里面append然后发送，这样就会有重复的信息，个人不推荐
                String filePath = null;
                String fileName = TimeUtil.getStrTimeToFileName(System.currentTimeMillis()) + ".txt";
                if (isCrash) {
                    filePath = FileUtil.getCachePath("log/crash", fileName);
                } else {
                    filePath = FileUtil.getCachePath("log/except", fileName);
                    return;
                }
                File file = new File(filePath);
                try {
                    FileOutputStream fos = new FileOutputStream(file, true);
                    fos.write((message).getBytes());
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                }
                Looper.loop();
            }

        }.start();
        return false;
    }

    // 使用HTTP Post 发送错误报告到服务器 这里不再赘述
    // private void postReport(File file) {
    // 在上传的时候还可以将该app的version，该手机的机型等信息一并发送的服务器，
    // Android的兼容性众所周知，所以可能错误不是每个手机都会报错，还是有针对性的去debug比较好
    // }

    public static void createBug() {
        try {// 制造bug
            File file = new File(Environment.getExternalStorageState(), "crash.bin");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            fis.read(buffer);
        } catch (Exception e) {
            // 这里不能再向上抛异常，如果想要将log信息保存起来，则抛出runtime异常，
            // 让自定义的handler来捕获，统一将文件保存起来上传
            throw new RuntimeException(e);
        }
    }

    public static String toStackTraceString(Throwable throwable) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(throwable.toString() + "\n");

        StackTraceElement[] stack = throwable.getStackTrace();
        for (StackTraceElement element : stack) {
            stringBuffer.append("\tat " + element + "\n");
        }

        StackTraceElement[] parentStack = stack;
        while (throwable != null) {
            stringBuffer.append("Caused by: " + "\n");
            stringBuffer.append(throwable + "\n");
            StackTraceElement[] currentStack = throwable.getStackTrace();
            int duplicates = countDuplicates(currentStack, parentStack);
            for (int i = 0; i < currentStack.length - duplicates; i++) {
                stringBuffer.append("\tat " + currentStack[i] + "\n");
            }
            if (duplicates > 0) {
                stringBuffer.append("\t... " + duplicates + " more" + "\n");
            }
            parentStack = currentStack;
            throwable = throwable.getCause();
        }
        return stringBuffer.toString();
    }

    private static int countDuplicates(StackTraceElement[] currentStack, StackTraceElement[] parentStack) {
        int duplicates = 0;
        int parentIndex = parentStack.length;
        for (int i = currentStack.length; --i >= 0 && --parentIndex >= 0; ) {
            StackTraceElement parentFrame = parentStack[parentIndex];
            if (parentFrame.equals(currentStack[i])) {
                duplicates++;
            } else {
                break;
            }
        }
        return duplicates;
    }
}