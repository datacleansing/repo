FROM qlik/gradle

EXPOSE 8080

# Copy application code.
COPY . /app/

# Install dependencies.
RUN gradle build
