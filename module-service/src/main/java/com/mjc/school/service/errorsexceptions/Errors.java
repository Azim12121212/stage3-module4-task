package com.mjc.school.service.errorsexceptions;

public enum Errors {
    ERROR_COMMAND_NOT_FOUND("0001", "Command not found."),
    ERROR_NEWS_ID_FORMAT("0011", "News Id should be number"),
    ERROR_NEWS_ID_VALUE("0012", "News id can not be null or less than 1. News id is: "),
    ERROR_NEWS_ID_NOT_EXIST("0013", "News with id {id} does not exist."),
    ERROR_NEWS_TITLE_LENGTH("0021", "News title can not be less than 5 and more than 30 symbols. News title is: "),
    ERROR_NEWS_TITLE_NOT_EXIST("0022", "News with title {id} do not exist."),
    ERROR_NEWS_CONTENT_LENGTH("0023", "News content can not be less than 5 and more than 255 symbols. News content is: "),
    ERROR_NEWS_CONTENT_NOT_EXIST("0024", "News with content {id} do not exist."),
    ERROR_AUTHOR_ID_FORMAT("0031", "Author Id should be number"),
    ERROR_AUTHOR_ID_VALUE("0032", "Author id can not be null or less than 1. Author id is: "),
    ERROR_AUTHOR_ID_NOT_EXIST("0033", "Author with id {id} does not exist."),
    ERROR_AUTHOR_NAME_LENGTH("0041", "Author name can not be less than 3 and more than 15 symbols. Author name is: "),
    ERROR_AUTHOR_NAME_NOT_EXIST("0042", "News with author name {id} does not exist."),
    ERROR_TAG_ID_FORMAT("0051", "Tag Id should be number"),
    ERROR_TAG_ID_VALUE("0052", "Tag id can not be null or less than 1. Tag id is: "),
    ERROR_TAG_ID_NOT_EXIST("0053", "Tag with id {id} does not exist."),
    ERROR_TAG_NAME_LENGTH("0061", "Tag name can not be less than 3 and more than 15 symbols. Tag name is: "),
    ERROR_TAG_NAME_NOT_EXIST("0062", "News with tag name {id} do not exist."),
    ERROR_COMMENT_ID_FORMAT("0071", "Comment Id should be number"),
    ERROR_COMMENT_ID_VALUE("0072", "Comment Id can not be null or less than 1. Comment id is: "),
    ERROR_COMMENT_ID_NOT_EXIST("0073", "Comment with id {id} does not exist."),
    ERROR_COMMENT_CONTENT_LENGTH("0074", "Comment content can not be less than 5 and more than 255 symbols. Comment content is: "),
    ERROR_COMMENT_BY_NEWS_ID("0081", "Comments with news id {id} do not exist");

    private String errorCode;
    private String errorMessage;

    private Errors() {
    }

    private Errors(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorData(String insert, boolean replace) {
        String result = "ERROR_CODE: " + getErrorCode() + " ERROR_MESSAGE: ";
        if (replace) {
            result += getErrorMessage().replace("{id}", insert);
        } else {
            result += getErrorMessage() + insert;
        }
        return result;
    }
}