import os
import pickle
import nltk
from nltk.corpus import stopwords
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer

def main():
    files_pos = os.listdir('txt_sentoken/pos')
    files_pos = [open('txt_sentoken/pos/'+f, 'r').read() for f in files_pos]
    files_neg = os.listdir('txt_sentoken/neg')
    files_neg = [open('txt_sentoken/neg/'+f, 'r').read() for f in files_neg]

    print("Sentence: ")
    print("Positive")
    split_list = list(map(lambda file: len(file.split("\n")), files_pos))
    print(f"max: {max(split_list)}")
    print(f"min: {min(split_list)}")
    print(f"avg: {sum(split_list)/len(split_list)}\n")

    print("Negative")
    split_list = list(map(lambda file: len(file.split("\n")), files_neg))
    print(f"max: {max(split_list)}")
    print(f"min: {min(split_list)}")
    print(f"avg: {sum(split_list)/len(split_list)}\n")

    print("Token:")
    print("Positive")
    split_list = list(map(lambda file: len(file.split()), files_pos))
    print(f"max: {max(split_list)}")
    print(f"min: {min(split_list)}")
    print(f"avg: {sum(split_list)/len(split_list)}\n")

    print("Negative:")
    split_list = list(map(lambda file: len(file.split()), files_neg))
    print(f"max: {max(split_list)}")
    print(f"min: {min(split_list)}")
    print(f"avg: {sum(split_list)/len(split_list)}\n")

    print("Whole collection sentence")
    split_list = list(map(lambda file: len(file.split("\n")), files_pos + files_neg))
    print(f"avg: {sum(split_list)/len(split_list)}\n")


if __name__ == "__main__":
    main()