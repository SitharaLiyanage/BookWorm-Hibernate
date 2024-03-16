package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        LOGIN, USER, BRANCH, BOOK, TRANSACTION
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case LOGIN:
                return new LoginBOImpl();
            case USER:
                return new UserBOImpl();
            case BRANCH:
                return new BranchBOImpl();
            case BOOK:
                return new BookBOImpl();
            case TRANSACTION:
                return new TransactionBOImpl();
            default:
                return null;
        }
    }
}
