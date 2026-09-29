package cl.ufro.dci.api.domain.dto;

public class ErrorDto {


    private String errMessage;
    private String errArgument;

    public ErrorDto(String errMessage, String errArgument) {
        this.errMessage = errMessage;
        this.errArgument = errArgument;
    }

}
