/*
 * Copyright (c) 2020. Red Hat, Inc. and/or its affiliates.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mvel3.methodutils;

import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.CodeElement;
import java.lang.classfile.MethodModel;
import java.lang.classfile.Opcode;
import java.lang.classfile.instruction.*;

/**
 * Extracts a normalized string representation of a method's bytecode for deduplication.
 * <p>
 * Two method implementations with identical bytecode produce identical strings,
 * enabling Murmur3-based hashing for node sharing in {@link org.mvel3.ClassManager}.
 * Debug info (line numbers, local variable names) is excluded so that functionally
 * identical methods always match regardless of compilation metadata.
 * <p>
 * Migrated from ASM (org.objectweb.asm) to JDK 25 Classfile API (java.lang.classfile).
 */
public class MethodByteCodeExtractor {

    /**
     * Extract bytecode instructions for the named method as a string.
     * Returns null if the method is not found.
     */
    public static String extract(final String methodName, final byte[] bytes) {
        ClassModel classModel = ClassFile.of().parse(bytes);

        for (MethodModel method : classModel.methods()) {
            if (method.methodName().equalsString(methodName)) {
                return extractMethodBody(method);
            }
        }
        return null;
    }

    private static String extractMethodBody(MethodModel method) {
        StringBuilder sb = new StringBuilder();

        method.code().ifPresent(code -> {
            for (CodeElement element : code) {
                switch (element) {
                    case ConstantInstruction ci -> {
                        Object val = ci.constantValue();
                        if (val == null) {
                            sb.append("const null\n");
                        } else {
                            sb.append("const ").append(val).append('\n');
                        }
                    }
                    case LoadInstruction li ->
                        sb.append(li.opcode().name()).append(' ').append(li.slot()).append('\n');
                    case StoreInstruction si ->
                        sb.append(si.opcode().name()).append(' ').append(si.slot()).append('\n');
                    case InvokeInstruction ii ->
                        sb.append(ii.opcode().name()).append(' ')
                          .append(ii.owner().asInternalName()).append('.')
                          .append(ii.name().stringValue())
                          .append(ii.typeSymbol().descriptorString()).append('\n');
                    case FieldInstruction fi ->
                        sb.append(fi.opcode().name()).append(' ')
                          .append(fi.owner().asInternalName()).append('.')
                          .append(fi.name().stringValue())
                          .append(fi.typeSymbol().descriptorString()).append('\n');
                    case TypeCheckInstruction tci ->
                        sb.append(tci.opcode().name()).append(' ')
                          .append(tci.type().asInternalName()).append('\n');
                    case NewObjectInstruction noi ->
                        sb.append("NEW ").append(noi.className().asInternalName()).append('\n');
                    case BranchInstruction bi ->
                        sb.append("jump ").append(bi.opcode().name()).append('\n');
                    case OperatorInstruction oi ->
                        sb.append(oi.opcode().name()).append('\n');
                    case ConvertInstruction ci ->
                        sb.append(ci.opcode().name()).append('\n');
                    case ReturnInstruction ri ->
                        sb.append(ri.opcode().name()).append('\n');
                    case StackInstruction si ->
                        sb.append(si.opcode().name()).append('\n');
                    case IncrementInstruction ii ->
                        sb.append("IINC ").append(ii.slot()).append(' ').append(ii.constant()).append('\n');
                    case ArrayLoadInstruction ali ->
                        sb.append(ali.opcode().name()).append('\n');
                    case ArrayStoreInstruction asi ->
                        sb.append(asi.opcode().name()).append('\n');
                    case NewPrimitiveArrayInstruction npai ->
                        sb.append("NEWARRAY ").append(npai.typeKind()).append('\n');
                    case NewReferenceArrayInstruction nrai ->
                        sb.append("ANEWARRAY ").append(nrai.componentType().asInternalName()).append('\n');
                    case ThrowInstruction _ ->
                        sb.append("ATHROW\n");
                    case MonitorInstruction mi ->
                        sb.append(mi.opcode().name()).append('\n');
                    case NopInstruction _ ->
                        sb.append("NOP\n");
                    // Skip debug/metadata elements (line numbers, labels, local vars, etc.)
                    default -> {}
                }
            }
        });

        return sb.toString();
    }
}
