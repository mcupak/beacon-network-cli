/*
 * Copyright 2016 Artem (tema.voskoboynick@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dnastack.bob.cli.converters;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.EnumOptionHandler;
import org.kohsuke.args4j.spi.Messages;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 * Copy of the {@link EnumOptionHandler} to uses enum values (instead of names) during conversion.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class EnumByIdOptionHandler<T extends Enum<T>> extends EnumOptionHandler<T> {
    private final Class<T> enumType;

    @SuppressWarnings("unchecked")
    public EnumByIdOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super T> setter) {
        super(parser, option, setter, setter.asFieldSetter().getType());
        enumType = setter.asFieldSetter().getType();
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        String s = params.getParameter(0).replaceAll("-", "_");
        T value = null;
        for (T o : enumType.getEnumConstants())
            if (o.toString().equalsIgnoreCase(s)) {
                value = o;
                break;
            }

        if (value == null) {
            if (option.isArgument()) {
                throw new CmdLineException(owner, Messages.ILLEGAL_OPERAND, option.toString(), s);
            } else {
                throw new CmdLineException(owner, Messages.ILLEGAL_OPERAND, params.getParameter(-1), s);
            }
        }
        setter.addValue(value);
        return 1;
    }
}
