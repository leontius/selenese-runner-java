package jp.vmi.selenium.selenese.parser;

import java.util.Iterator;

import jp.vmi.selenium.selenese.side.Side;
import jp.vmi.selenium.selenese.side.SideSuite;
import jp.vmi.selenium.selenese.side.SideTest;

/**
 * Iterator and iterable of test suite of SideFile format.
 */
public class SideTestCaseIterator extends AbstractTestElementIterator<TestCaseEntry> implements TestCaseIterator {

    private final Side side;
    private final Iterator<String> iter;

    /**
     * Constructor.
     *
     * @param side side format data.
     * @param testSuiteId test suite id.
     */
    public SideTestCaseIterator(Side side, String testSuiteId) {
        super(side.getFilename());
        this.side = side;
        SideSuite suite = side.getSuiteMap().get(testSuiteId);
        setName(suite.getName());
        setId(suite.getId());
        this.iter = suite.getTests().iterator();
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public TestCaseEntry next() {
        String testId = iter.next();
        SideTest test = side.getTestMap().get(testId);
        return new TestCaseEntry(true, test.getId(), test.getName());
    }

    @Override
    public TestElementIteratorFactory<CommandIterator, TestCaseEntry> getCommandIteratorFactory() {
        return caseEntry -> new SideCommandIterator(side, caseEntry.id);
    }
}
