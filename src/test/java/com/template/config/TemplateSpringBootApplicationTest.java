package com.template.config;

import org.junit.jupiter.api.Test;

public class TemplateSpringBootApplicationTest {

    /**
     * Just simple test for jacoco coverage.
     */
    @Test
    void mainTest() {
//        // Mock with CALLS_REAL_METHODS as Mockito can't spy static methods(or I didn't find, I tried but not long time)
//        try (MockedStatic<TemplateSpringBootApplication> mockedStatic = Mockito
//                .mockStatic(TemplateSpringBootApplication.class
//                        , Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS))) {
//            mockedStatic.when(() -> TemplateSpringBootApplication.main(null))
//                    .thenAnswer((Answer<Void>) invocation -> null);
//            TemplateSpringBootApplication.main(null);
//            mockedStatic.verify(() -> TemplateSpringBootApplication.main(null)
//                    , Mockito.times(1));
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());

        //TemplateSpringBootApplication.main(new String[]{});
    }
}
