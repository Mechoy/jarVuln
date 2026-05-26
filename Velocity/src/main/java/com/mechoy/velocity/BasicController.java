/*
 * Copyright 2013-2018 the original author or authors.
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

package com.mechoy.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@RestController
@RequestMapping("/ssti")
public class BasicController {

    @GetMapping("/velocity")
    public void velocity(String template) throws Exception {
        Velocity.init();

        VelocityContext context = new VelocityContext();

        context.put("author", "Elliot A.");
        context.put("address", "217 E Broadway");
        context.put("phone", "555-1337");

        StringWriter swOut = new StringWriter();
        Velocity.evaluate(context, swOut, "test", template);
    }

    public static void main(String[] args) throws Exception {
        Method[] methods = BasicController.class.getMethods();
        System.out.println(methods[1]);
        Type[] genericParameterTypes = methods[1].getGenericParameterTypes();
        System.out.println(genericParameterTypes[0]);
    }
}
