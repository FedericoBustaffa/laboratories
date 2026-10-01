from nltk import NLTKWordTokenizer

if __name__ == "__main__":
    tokenizer = NLTKWordTokenizer()
    s = "hello world, how do you feel today?"

    for s in tokenizer.tokenize(s):
        print(s)
