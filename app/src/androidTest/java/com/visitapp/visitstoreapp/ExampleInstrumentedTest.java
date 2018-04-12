package com.visitapp.visitstoreapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.AsociacionController;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.visitapp.visitstoreapp", appContext.getPackageName());

        Asociacion a1 = new Asociacion();
        a1.setNombre("Asociació 1");
        a1.setObservaciones("Asociació creada test");
        a1.setLogo("logo pendent");
        AsociacionController a1Cont = new AsociacionController(a1);
        a1Cont.save();
        System.out.println("TERMINA TEST");

    }
}
