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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * {@link Provider} that reads from an {@link InputStream} or {@link Reader}.
 */
public class StreamProvider implements Provider {

    private final Reader reader;

    public StreamProvider(Reader reader) {
        this.reader = reader;
    }

    public StreamProvider(InputStream stream) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(stream));
    }

    public StreamProvider(InputStream stream, String charsetName) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(stream, charsetName));
    }

    @Override
    public int read(char[] buffer, int off, int len) throws IOException {
        int result = reader.read(buffer, off, len);

        if (result == 0) {
            if (off < buffer.length && len > 0) {
                result = -1;
            }
        }

        return result;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
