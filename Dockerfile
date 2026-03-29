FROM eclipse-temurin:21-jdk

WORKDIR /workspace

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl python3 \
    && rm -rf /var/lib/apt/lists/*

# Keep wrapper and Gradle caches outside the bind mount for faster repeated builds.
ENV GRADLE_USER_HOME=/gradle-cache

CMD ["bash"]
