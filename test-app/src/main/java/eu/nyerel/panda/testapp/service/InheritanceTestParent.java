package eu.nyerel.panda.testapp.service;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class InheritanceTestParent extends InheritanceTestGrandpa {

    public void doNothing(String someInput) {
        System.out.println("Doing nothing in " + InheritanceTestParent.class.getSimpleName());
        super.doNothing(someInput);
    }

}
