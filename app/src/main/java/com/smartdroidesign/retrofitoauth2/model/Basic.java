package com.smartdroidesign.retrofitoauth2.model;

/**
 *
 * @param <T> whatever type of data you supply to it (an ArrayList of images, in this specific case)
 */

public class Basic<T> {

    public int status; // status code
    public boolean success; // successful or not
    public T data; // generic type of data

}
