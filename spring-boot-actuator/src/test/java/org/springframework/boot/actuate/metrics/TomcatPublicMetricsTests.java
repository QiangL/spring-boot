/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.actuate.metrics;

import java.util.Iterator;

import org.junit.Test;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TomcatPublicMetrics}
 *
 * @author Johannes Edmeier
 * @author Phillip Webb
 */
public class TomcatPublicMetricsTests {

	@Test
	public void tomcatMetrics() throws Exception {
		try (AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(
				Config.class)) {
			TomcatPublicMetrics tomcatMetrics = context
					.getBean(TomcatPublicMetrics.class);
			Iterator<Metric<?>> metrics = tomcatMetrics.metrics().iterator();
			assertThat(metrics.next().getName()).isEqualTo("httpsessions.max");
			assertThat(metrics.next().getName()).isEqualTo("httpsessions.active");
			assertThat(metrics.hasNext()).isFalse();
		}
	}

	@Configuration
	static class Config {

		@Bean
		public TomcatServletWebServerFactory webServerFactory() {
			return new TomcatServletWebServerFactory(0);
		}

		@Bean
		public TomcatPublicMetrics metrics() {
			return new TomcatPublicMetrics();
		}

	}

}
