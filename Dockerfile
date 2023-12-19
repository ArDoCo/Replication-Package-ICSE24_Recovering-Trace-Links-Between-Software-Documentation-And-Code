FROM python:3.7 AS CodeBERT
WORKDIR /replication/baselines/CodeBERT
COPY ./baselines/CodeBERT/requirement.txt .
RUN python3 -m venv --copies venv && \
    . venv/bin/activate && \
    python3 -m pip install -r requirement.txt
COPY ./baselines/CodeBERT .

FROM python:3.9 AS FineGrainedTraceability
WORKDIR /replication/baselines/finegrained-traceability
RUN python3 -m venv --copies venv && \
    . venv/bin/activate && \
    python3 -m pip install fasttext~=0.9.2 javalang~=0.13.0 pycparser~=2.21 comment-parser~=1.2.4 esprima~=4.0.1 XlsxWriter~=3.0.1 spacy~=3.1.1 pydantic~=1.8.2 typing-extensions~=4.2.0 nltk~=3.2.5 numpy~=1.22.3 scikit-learn~=1.1.1 pandas~=1.1.5 joblib~=1.1.0 autograd~=1.3 torch~=1.13.1 transformers~=4.26.1 scipy~=1.8.1 pyemd~=0.5.1 gensim~=3.6.0
COPY ./baselines/finegrained-traceability .


FROM ubuntu:22.04
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y \
    openjdk-17-jdk maven vim nano curl software-properties-common gnupg git \
    && add-apt-repository -y ppa:deadsnakes \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends python3.7-dev python3.7-distutils python3.9-dev python3.9-distutils \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* 

WORKDIR /replication
COPY . .

RUN rm -r /replication/baselines/CodeBERT
RUN rm -r /replication/baselines/finegrained-traceability
COPY --from=CodeBERT /replication/baselines/CodeBERT /replication/baselines/CodeBERT
COPY --from=FineGrainedTraceability /replication/baselines/finegrained-traceability /replication/baselines/finegrained-traceability

WORKDIR /replication/baselines/finegrained-traceability
RUN . venv/bin/activate && \
    python3 -m spacy download it_core_news_lg && \
    python3 -m spacy download en_core_web_lg && \
    python3 -m nltk.downloader stopwords && \
    python3 -m nltk.downloader punkt && \
    python3 -m nltk.downloader wordnet && \
    deactivate

# Copy FASTTEXT Models
WORKDIR /replication/baselines/models
ADD https://dl.fbaipublicfiles.com/fasttext/vectors-crawl/cc.en.300.bin.gz .
ADD https://dl.fbaipublicfiles.com/fasttext/vectors-crawl/cc.it.300.bin.gz .
RUN gunzip cc.en.300.bin.gz && gunzip cc.it.300.bin.gz

WORKDIR /replication

## Clone Source Code of Benchmarks
RUN rm -r /replication/baselines/finegrained-traceability/datasets/bigbluebutton/code && git clone https://github.com/ArDoCo/bigbluebutton.git /replication/baselines/finegrained-traceability/datasets/bigbluebutton/code && \
    rm -r /replication/baselines/finegrained-traceability/datasets/jabref/code && git clone https://github.com/ArDoCo/jabref.git /replication/baselines/finegrained-traceability/datasets/jabref/code && \
    rm -r /replication/baselines/finegrained-traceability/datasets/MediaStore/code && git clone https://github.com/ArDoCo/MediaStore3.git /replication/baselines/finegrained-traceability/datasets/MediaStore/code && \
    rm -r /replication/baselines/finegrained-traceability/datasets/teammates/code && git clone https://github.com/ArDoCo/teammates.git /replication/baselines/finegrained-traceability/datasets/teammates/code && \
    rm -r /replication/baselines/finegrained-traceability/datasets/TeaStore/code && git clone https://github.com/ArDoCo/TeaStore.git /replication/baselines/finegrained-traceability/datasets/TeaStore/code

# Cache MAVEN Dependencies
RUN cd ardoco+arcotl && mvn -P tlr -B dependency:resolve -Dclassifier=test
RUN cd baselines/TAROT && mvn -B dependency:resolve

# Run Eval once:
# RUN cd ardoco+arcotl && mvn -P tlr clean test -Dsurefire.failIfNoSpecifiedTests=false -Dtest=TraceLinkEvaluationIT
# RUN cd baselines/TAROT && mvn -B compile exec:java -Dexec.mainClass="Start"

ENTRYPOINT [ "/bin/bash", "-c", "cat README.md && bash" ]

