package tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Main {
    @Test
    public void getparent(){
        ChildNode childNode = new ChildNode();
        childNode.setNodeCode("code1");
        childNode.setNextCode("code2,code3,code4");

        ChildNode childNode2 = new ChildNode();
        childNode2.setNodeCode("code2");
        childNode2.setNextCode("code5");

        ChildNode childNode3 = new ChildNode();
        childNode3.setNodeCode("code3");
        childNode3.setNextCode("code5");

        ChildNode childNode4 = new ChildNode();
        childNode4.setNodeCode("code4");
        childNode4.setNextCode("code5");


        childNode.setConditionNodes(new ArrayList<>(){{
            add(childNode2);
            add(childNode3);
            add(childNode4);
        }});

        ChildNode childNode5 = new ChildNode();
        childNode5.setNodeCode("code5");
        childNode5.setNextCode(null);

        childNode.setChildNode(childNode5);

    }
}
