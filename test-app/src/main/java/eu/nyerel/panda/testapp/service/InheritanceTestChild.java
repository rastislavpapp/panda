package eu.nyerel.panda.testapp.service;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class InheritanceTestChild extends InheritanceTestParent {

    @Override
    public void doNothing(String someInput) {
        System.out.println("Doing nothing in " + InheritanceTestChild.class.getSimpleName());
        super.doNothing(someInput);
    }

}
