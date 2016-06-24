package net.achalaggarwal.arbiter.workflow.node;

import net.achalaggarwal.arbiter.Action;
import net.achalaggarwal.arbiter.workflow.Node;
import org.xembly.Directives;

import static java.lang.String.format;

public class JoinNode extends Node {
  private Action self;
  private Action transitionNode = null;


  public JoinNode(Action self, Action transitionNode) {
    if (transitionNode == null) {
      throw new RuntimeException(format("No transition found for join action %s", self.getName()));
    }

    this.self = self;
    this.transitionNode = transitionNode;
  }

  @Override
  public Directives buildNode() {
    return new Directives()
      .add("join")
      .attr("name", self.getName())
      .attr("to", transitionNode.getName())
      .up();
  }
}
