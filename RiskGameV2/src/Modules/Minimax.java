package Modules;

import Models.Attack;
import Models.TreeNode;
import Models.FinalValues;

import java.util.ArrayList;

public class Minimax {

    private static int INFP = Integer.MAX_VALUE;
    private static int INFN = Integer.MIN_VALUE;
    private static ArrayList<TreeNode> finalMoves=new ArrayList<>(0);
    ;
   //todo : Add check game is finished in gamelogic ?
//    public Minimax()
//    {
//        finalMoves = new ArrayList<>(0);
//    }

    public static TreeNode minimaxFunc (TreeNode position , double alpha , double beta) {
        //todo: if if(depth == 0 || game is over )
//        System.out.println("      position: " + position.getNodeIndex());

        if(position.getDepth_node() == 0)
            //todo : we can add evaluate board here instead of node value
            return position;

        TreeNode maxEvalNode;
        TreeNode minEvalNode;
        TreeNode evalNode;

        if(position.isType_node()){
            maxEvalNode = new TreeNode(true , INFN , position.getDepth_node() - 1);
            for (TreeNode child:position.getChildren_node()) {
                evalNode = minimaxFunc(child , alpha , beta);
                evalNode.setChildren_node(null);
                if(evalNode.getValue_node() > maxEvalNode.getValue_node())
                    maxEvalNode = evalNode;
                alpha = Double.max(alpha , evalNode.getValue_node());
                if(beta <= alpha)
                    break;
            }

//            System.out.println("added max  :  " + maxEvalNode.getNodeIndex() +"  pos : " + position.getNodeIndex());
            finalMoves.add(maxEvalNode);
            finalMoves.add(position);
            return maxEvalNode;
        }
        else{
            minEvalNode = new TreeNode(false , INFP , position.getDepth_node() - 1);
            for (TreeNode child: position.getChildren_node()) {
                evalNode = minimaxFunc(child , alpha , beta);
                evalNode.setChildren_node(null);
                if(evalNode.getValue_node() < minEvalNode.getValue_node())
                  minEvalNode = evalNode;


                beta = Double.min(beta , evalNode.getValue_node());
                if(beta <= alpha)
                    break;
            }

//            System.out.println("added min:  " + minEvalNode.getNodeIndex() + "  pos : " + position.getNodeIndex());
            finalMoves.add(minEvalNode);
            finalMoves.add(position);

            return minEvalNode;
        }

    }

    public static void main(String[] args) {

        TreeNode start = new TreeNode(true , 0 , 3  , 0); //n0

        TreeNode n1 = new TreeNode(true , 0 , 1 , 1);
        TreeNode n2 = new TreeNode(true , 0 , 1 , 2 );
        TreeNode n3 = new TreeNode(true , 0 , 1 , 3 );
        TreeNode n4 = new TreeNode(true , 0 , 1 , 4);

        TreeNode n5 = new TreeNode(false , 0 , 2 , 5);
        TreeNode n6 = new TreeNode(false , 0 , 2 , 6);

        TreeNode n7 = new TreeNode(false , -1 , 0 , 7 );
        TreeNode n8 = new TreeNode(false , 3 , 0 , 8 );
        TreeNode n9 = new TreeNode(false , 5 , 0  , 9);
        TreeNode n10 = new TreeNode(false , 1 , 0 , 10);
        TreeNode n11 = new TreeNode(false , -6 , 0 , 11);
        TreeNode n12 = new TreeNode(false , -4 , 0 , 12);
        TreeNode n13 = new TreeNode(false , 0 , 0 , 13 );
        TreeNode n14 = new TreeNode(false , 9 , 0 , 14 );

        start.addChild(n5);
        start.addChild(n6);

        n5.addChild(n1);
        n5.addChild(n2);

        n6.addChild(n3);
        n6.addChild(n4);

        n1.addChild(n7);
        n1.addChild(n8);

        n2.addChild(n9);
        n2.addChild(n10);

        n3.addChild(n11);
        n3.addChild(n12);

        n4.addChild(n13);
        n4.addChild(n14);


        int result_node_index = minimaxFunc(start , INFN , INFP).getNodeIndex();
        ArrayList<TreeNode> minimaxOutput = new ArrayList<>(0);
//        System.out.println(result_node_index);
        for (int i = finalMoves.size()-1; i >0 ; i--) {

            //todo : add && type - 1 = max node   and include only maximum 3 nodes
            if(finalMoves.get(i-1).getNodeIndex() == result_node_index)
                minimaxOutput.add(finalMoves.get(i));

        }

        minimaxOutput.add(finalMoves.get(0));

        for (TreeNode tn: minimaxOutput) {
//            System.out.println(tn.getLastAttack().toString());
        }



    }

    public static Attack getNextAttack(TreeNode start){
        TreeNode result = minimaxFunc(start , INFN , INFP);
        ArrayList<TreeNode> minimaxOutput = new ArrayList<>(0);
        int result_node_index = result.getNodeIndex();
//        System.out.println(result_node_index);
        for (int i = finalMoves.size()-1; i >0 ; i--) {

            //todo : add && type - 1 = max node   and include only maximum 3 nodes
            if(finalMoves.get(i-1).getNodeIndex() == result_node_index) {
                minimaxOutput.add(finalMoves.get(i));
            }

        }

//        minimaxOutput.add(finalMoves.get(0));


//        System.out.println("start to print");
        for (TreeNode tn: minimaxOutput) {
            if (tn.getDepth_node() == FinalValues.TREE_MAX_LEVEL - 1 && tn.getLastAttack() != null) {
                System.out.println(tn.getLastAttack().toString() + "  " +  tn.getDepth_node());

                return tn.getLastAttack();
            }
//            System.out.println(tn.getLastAttack().toString() + "  " +  tn.getDepth_node());
        }
        return null;

    }

}
