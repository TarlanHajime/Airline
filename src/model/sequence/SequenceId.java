package model.sequence;

public class SequenceId {
    private static long idSeq;

    public static long nextVal() {
        return ++idSeq;
    }

}
