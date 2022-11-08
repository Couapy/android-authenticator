package fr.utt.if26.mmarchan;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.util.Calendar;

import de.taimos.totp.TOTP;
import de.taimos.totp.TOTPData;

public class CodeProvider {
    private final Calendar calendar = Calendar.getInstance();
    private final TOTPData totp;

    CodeProvider(String issuer, String user, String secret) {
        this.totp = new TOTPData(issuer, user, (new Base32()).decode(secret));
    }

    public String getIssuer() {
        return this.totp.getIssuer();
    }

    public String getUser() {
        return this.totp.getUser();
    }

    public int getValidSeconds() {
        return 30 - (Calendar.getInstance().get(Calendar.SECOND) % 30);
    }

    public String getTOTPCode() {
        return TOTP.getOTP(Hex.encodeHexString(this.totp.getSecret()));
    }
}
