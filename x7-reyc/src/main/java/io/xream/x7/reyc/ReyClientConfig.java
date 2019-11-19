/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.xream.x7.reyc;

import com.github.kristofa.brave.httpclient.BraveHttpRequestInterceptor;
import com.github.kristofa.brave.httpclient.BraveHttpResponseInterceptor;
import io.xream.x7.reyc.internal.HttpClientResolver;
import io.xream.x7.reyc.internal.HttpClientProperies;
import io.xream.x7.reyc.internal.ReyClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({HttpClientProperies.class,ReyClientProperties.class})
public class ReyClientConfig {


    public ReyClientConfig(HttpClientProperies properies, ReyClientProperties reyClientProperties,ReyTemplate reyTemplate){

        HttpClientResolver.init(properies, reyClientProperties,reyTemplate);

    }

    @ConditionalOnBean({BraveHttpRequestInterceptor.class,BraveHttpResponseInterceptor.class})
    @Bean
    public ReyTracing enableReyTracing(BraveHttpRequestInterceptor req,BraveHttpResponseInterceptor rep){
        HttpClientResolver.initInterceptor(req,  rep);
        return new ReyTracing();
    }
}
