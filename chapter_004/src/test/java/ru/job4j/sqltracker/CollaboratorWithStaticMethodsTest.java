//package ru.job4j.sqltracker;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.mockito.internal.verification.VerificationModeFactory.times;
//import static org.powermock.api.mockito.PowerMockito.*;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(CollaboratorWithStaticMethods.class)
//public class CollaboratorWithStaticMethodsTest {
//    @Test(expected = RuntimeException.class)
//    //@Test
//    public void givenStaticMethods() {
//        mockStatic(CollaboratorWithStaticMethods.class);
//
//        when(CollaboratorWithStaticMethods.
//                firstMethod(Mockito.anyString()))
//                .thenReturn("Hello Baeldung!");
//        when(CollaboratorWithStaticMethods.secondMethod()).
//                thenReturn("Nothing special");
//        doThrow(new RuntimeException()).
//                when(CollaboratorWithStaticMethods.class);
//        String thirdWelcome = CollaboratorWithStaticMethods.thirdMethod();
//
//        String firstWelcome = CollaboratorWithStaticMethods.
//                firstMethod("Whoever");
//        String secondWelcome = CollaboratorWithStaticMethods.
//                firstMethod("Whatever");
//
//        System.out.println(firstWelcome);
//        System.out.println(secondWelcome);
//        System.out.println(thirdWelcome);
//
//        assertEquals("Hello Baeldung!", firstWelcome);
//        assertEquals("Hello Baeldung!", secondWelcome);
//
//        verifyStatic(
//                CollaboratorWithStaticMethods.class,
//                times(2)
//        );
//        CollaboratorWithStaticMethods.firstMethod(Mockito.anyString());
//
//        verifyStatic(
//                CollaboratorWithStaticMethods.class,
//                Mockito.never()
//        );
//        CollaboratorWithStaticMethods.secondMethod();
//
//        CollaboratorWithStaticMethods.thirdMethod();
//    }
//}