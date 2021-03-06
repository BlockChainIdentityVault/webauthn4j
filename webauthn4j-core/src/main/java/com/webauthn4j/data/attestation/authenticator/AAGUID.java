/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.data.attestation.authenticator;

import com.webauthn4j.util.UUIDUtil;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class AAGUID implements Serializable {

    public static final AAGUID ZERO = new AAGUID(new byte[16]);
    public static final AAGUID NULL = new AAGUID((UUID) null);

    private final UUID value;

    public AAGUID(UUID value) {
        this.value = value;
    }

    public AAGUID(byte[] value) {
        if (value == null) {
            this.value = null;
        } else {
            this.value = UUIDUtil.fromBytes(value);
        }
    }

    public AAGUID(String value) {
        if (value == null) {
            this.value = null;
        } else {
            this.value = UUIDUtil.fromString(value);
        }
    }

    public UUID getValue() {
        return value;
    }

    public byte[] getBytes() {
        return UUIDUtil.convertUUIDToBytes(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AAGUID aaguid = (AAGUID) o;
        return Objects.equals(value, aaguid.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
