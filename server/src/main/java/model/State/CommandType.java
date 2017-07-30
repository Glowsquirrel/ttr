package model.State;

/**
 * Created by sjrme on 7/29/17.
 */

public enum CommandType {
    DRAW_THREE_DEST_CARDS,
    RETURN_DEST_CARD,
    RETURN_NO_DEST_CARD,
    DRAW_TRAIN_CARD_FROM_DECK,
    FACEUP_NON_LOCOMOTIVE,
    FACEUP_LOCOMOTIVE,
    CLAIM_ROUTE,
}
