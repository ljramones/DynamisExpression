/*
 * Copyright (C) 2007-2010 Julio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2016 The JavaParser Team.
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mvel3.parser;

import java.io.IOException;

/**
 * {@link Provider} that reads from a {@link String}.
 */
public class StringProvider implements Provider {

    private String string;
    private int position = 0;
    private final int size;

    public StringProvider(String string) {
        this.string = string;
        this.size = string.length();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int numCharsOutstanding = size - position;

        if (numCharsOutstanding == 0) {
            return -1;
        }

        int numBytesInBuffer = cbuf.length;
        int numBytesToRead = numBytesInBuffer - off;
        numBytesToRead = Math.min(numBytesToRead, len);

        if (numBytesToRead > numCharsOutstanding) {
            numBytesToRead = numCharsOutstanding;
        }

        string.getChars(position, position + numBytesToRead, cbuf, off);

        position += numBytesToRead;

        return numBytesToRead;
    }

    @Override
    public void close() throws IOException {
        string = null;
    }
}
