# Install dependencies

The following steps describe the setup to run the code locally.

Run these lines one after another in your command line:

    pip install fasttext==0.9.2 javalang==0.13.0 XlsxWriter==3.0.1 spacy==3.1.1 nltk==3.2.5 gensim==3.6.0 pandas==1.1.5 sklearn==0.0 autograd==1.3
    python -m spacy download it_core_news_lg
    python -m spacy download en_core_web_trf
    python
    import nltk
    nltk.download("stopwords")
    nltk.download("punkt")
    nltk.download("wordnet")
    
* `fasttext` is the dependency for the fasttext word embedding library
* `javalang` is the java AST parser
* `XlsxWriter` is needed to create excel files (to write evaluation results)
* `spacy` is needed for its lemmatizer
* `nltk` is needed for its stopwords, tokenizer and lemmatizer/stemmer

The fastText model files (for english and italian) are not included in this repository. The model files can be found on the [website of fastText](https://fasttext.cc/docs/en/crawl-vectors.html). For the paper we used `cc.en.300.bin` and `cc.it.300.bin`.

To check the correct setup, run [App.py](./App.py).
