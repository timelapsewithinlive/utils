package exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ExceptionWithoutTraceStack extends RuntimeException{
    private String message;

    public ExceptionWithoutTraceStack(String message) {
        this.message = message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        //return super.fillInStackTrace();
        return null;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
