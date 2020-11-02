package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no such giftCertificate")
public class GiftCertificateNotFoundException extends RuntimeException {

    private long id;
    private String message;

    public GiftCertificateNotFoundException(long id) {
        this.id = id;
    }

    public GiftCertificateNotFoundException(String message) {
    }


    public long getId() {
        return id;
    }

}
