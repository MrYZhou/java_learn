package tree;

import lombok.Data;

import java.util.List;

@Data
public class ChildNode {
    private String nextCode;
    private String nodeCode;

    private ChildNode childNode;

    private List<ChildNode> conditionNodes;
}
