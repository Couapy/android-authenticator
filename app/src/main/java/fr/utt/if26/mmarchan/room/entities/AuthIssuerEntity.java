package fr.utt.if26.mmarchan.room.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.util.Calendar;

import de.taimos.totp.TOTP;

/**
 * Database entity for the code provider.
 * It provide TOTP code from the token by refreshing the instance using `refreshCode`.
 * Note: it depends on a section (using foreign key)
 */
@Entity(
        tableName = "auth_issuers",
        foreignKeys = @ForeignKey(
                entity = SectionEntity.class,
                parentColumns = "id",
                childColumns = "sectionId",
                onDelete = ForeignKey.CASCADE
        )
)
public class AuthIssuerEntity {
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

    @NonNull
    @ColumnInfo(name = "sectionId")
    public int sectionId;

    @Ignore
    public int remainingTime = 0;

    @Ignore
    public String code = "000000";

    public AuthIssuerEntity() {
        this.issuer = "Issuer";
        this.user = null;
        this.secret = "SECRET";
    }

    public AuthIssuerEntity(String issuer, String user, String secret, int sectionId) {
        this.issuer = issuer;
        this.user = user;
        this.secret = secret;
        this.sectionId = sectionId;
    }

    public void refreshCode() {
        // Remaining time
        remainingTime = 30 - (Calendar.getInstance().get(Calendar.SECOND) % 30);

        // TOTP code
        try {
            byte[] bytes = (new Base32()).decode(this.secret);
            code = TOTP.getOTP(Hex.encodeHexString(bytes));
        } catch (Exception e) {
            code = "Invalid token";
        }
    }

}
