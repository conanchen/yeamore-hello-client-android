package com.github.conanchen.yeamore.hello.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

/**
 * Created by conanchen on 2017/12/20.
 */

@Entity(indices = {
        @Index(value = {"id", "lastUpdated"})
})
public class Hello {
    @PrimaryKey
    @NonNull
    public int id;
    public String message;
    public long created;
    public long lastUpdated;

    public Hello() {
    }

    private Hello(@NonNull int id, String message, long created, long lastUpdated) {
        this.id = id;
        this.message = message;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private String message;
        private long created;
        private long lastUpdated;

        Builder() {
        }

        public Hello build() {
            String missing = "";
            if (id <= 0) {
                missing += " id must be > 0 ";
            }
            if (Strings.isNullOrEmpty(message)) {
                missing += " message";
            }

            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return null;
        }
    }
}