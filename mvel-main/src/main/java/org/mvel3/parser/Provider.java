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
 * Provides character-level input for the parser.
 */
public interface Provider {

    /**
     * Reads characters into an array.
     *
     * @param buffer Destination buffer
     * @param offset Offset at which to start storing characters
     * @param len    The maximum possible number of characters to read
     * @return The number of characters read, or -1 if all read
     * @throws IOException if an I/O error occurs
     */
    int read(char[] buffer, int offset, int len) throws IOException;

    /**
     * Closes the stream and releases any system resources associated with it.
     *
     * @throws IOException if an I/O error occurs
     */
    void close() throws IOException;
}
