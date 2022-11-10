package fr.utt.if26.mmarchan.room.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import de.taimos.totp.TOTP;

@Entity(tableName = "auth_issuers")
public class AuthIssuer {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "issuer")
    public String issuer;

    @ColumnInfo(name = "user")
    public String user;

    @NonNull
    @ColumnInfo(name = "secret")
    public String secret;

    public AuthIssuer() {
        this.issuer = "Issuer";
        this.user = null;
        this.secret = "SECRET";
    }

    public AuthIssuer(String issuer, String user, String secret) {
        this.issuer = issuer;
        this.user = user;
        this.secret = secret;
    }

    public String getTOTPCode() {
        byte[] bytes = (new Base32()).decode(this.secret);
        return TOTP.getOTP(Hex.encodeHexString(bytes));
    }
}
