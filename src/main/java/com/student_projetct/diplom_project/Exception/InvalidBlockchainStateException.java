package com.student_projetct.diplom_project.Exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class InvalidBlockchainStateException extends Exception{
    public InvalidBlockchainStateException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public InvalidBlockchainStateException() {
    }

    public InvalidBlockchainStateException(String message) {
        super(message);
    }
}
