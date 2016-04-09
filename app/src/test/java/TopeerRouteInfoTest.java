import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import java.util.LinkedHashMap;
import java.util.Map;

import dalvik.annotation.TestTargetClass;
import net.brotzeller.topeer.topo.RouteInfo;

public class TopeerRouteInfoTest {
    RouteInfo myInfo;
    String defaultName = "Foo";
    String defaultId = "4711";
    String defaultDifficulty = "5+";

    @Before
    public void setUp() {
        myInfo = getRouteInfo(defaultId, defaultName, defaultDifficulty);
    }

    private RouteInfo getRouteInfo(String i, String n, String d) {
        RouteInfo thing = new RouteInfo(i, n, d);
        return thing;
    }

    @Test
    public void RouteInfo_toString_printsPrettyTest() {
        String expectation = "4711: Foo - 5+\n";
        String result = myInfo.toString();
        assertTrue(result.equals(expectation));
    }

    @Test
    public void RouteInfo_checkIntegrity_isValidTest() {
        RouteInfo myInfo =  getRouteInfo("1", "Foo", "5+");
        assertTrue(myInfo.checkIntegrity());
    }
    @Test
    public void RouteInfo_checkIntegrity_isInvalidTest() {
        RouteInfo myInfo =  getRouteInfo("1", null, "5+");
        assertFalse(myInfo.checkIntegrity());
    }
    @Test
    public void RouteInfo_checkIntegrity_emptyIsInvalidTest() {
        RouteInfo myInfo =  new RouteInfo();
        assertFalse(myInfo.checkIntegrity());
    }

    @Test
    public void RouteInfo_nameProperty() {
        assert(myInfo.getName().equals(defaultName));
        myInfo.setName("Bar");
        assert(myInfo.getName().equals("Bar"));
    }
    @Test
    public void RouteInfo_idProperty() {
        assert(myInfo.getIndex().equals(defaultId));
        myInfo.setIndex("31337");
        assert(myInfo.getIndex().equals("31337"));
    }
    @Test
    public void RouteInfo_difficultyProperty() {
        assert(myInfo.getDifficulty().equals(defaultDifficulty));
        myInfo.setDifficulty("8-");
        assert(myInfo.getDifficulty().equals("8-"));
    }
    @Test
    public void RouteInfo_descriptionProperty() {
        assertNull(myInfo.getDescription());
        myInfo.setDescription("Foo Bar");
        assert(myInfo.getDescription().equals("Foo Bar"));
    }

}