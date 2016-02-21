package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.v4.util.Pair;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lendevsanadmin on 2/3/2016.
 */
public class EndpointsAsyncTaskAndroidTest extends AndroidTestCase implements EndpointsAsyncTask.AsyncResponse {
    final CountDownLatch signal = new CountDownLatch(1);
    public void testVerifyAsyncResponse() throws InterruptedException {

        Context context = getContext();
        //???????I don't understand why I had to use "this" and "context"; only "context" in both places did not work
        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(context, "Manfred"));
        signal.await();// wait for callback
    }

    @Override
    public void processFinish(String output){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        String expected = "This is a joke from the java library JokerProviderLibrary";
        assertEquals(expected, output);
        signal.countDown();// notify the count down latch
    }
}
