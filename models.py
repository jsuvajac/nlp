import os
import pickle
import nltk
import random

import numpy as np
from nltk.corpus import stopwords

from sklearn.model_selection import train_test_split

from nltk.classify.scikitlearn import SklearnClassifier
from sklearn.svm import SVC
from sklearn.ensemble import RandomForestClassifier
from sklearn.naive_bayes import MultinomialNB

from sklearn.metrics import classification_report, confusion_matrix, accuracy_score

from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer

from sklearn.model_selection import cross_val_score



from sklearn.model_selection import GridSearchCV
from sklearn.linear_model import LogisticRegression

def main():
    pickle_in =open("data/feat1.pkl", "rb")
    feat1 = pickle.load(pickle_in)
    pickle_in.close()

    pickle_in =open("data/feat2.pkl", "rb")
    feat2 = pickle.load(pickle_in)
    pickle_in.close()

    features = [feat1, feat2]
    feature_size = [500,1000,1500,2000,2500,3000]
    for d in features:
        for f in feature_size:
            random.shuffle(d)

            data = [i for i, j in d]
            target = [j for i, j in d]

            vectorizer = CountVectorizer(max_features=f, tokenizer=nltk.word_tokenize, stop_words='english')
            count = vectorizer.fit_transform(data)
            tfidf_transformer = TfidfTransformer()
            tf = tfidf_transformer.fit_transform(count)

            # split data 15% for cross validation 
            tf, x_cv, target, y_cv = train_test_split(tf, target, test_size=0.15)
            # split data 17% of the 85% for test data 
            x_train, x_test, y_train, y_test = train_test_split(tf, target, test_size=0.17)

            print(f'feature size: {f}')
            print()
            print('Multinomial Brenulli:')
            bay=MultinomialNB()
            bay.fit(x_train, y_train)
            predictions = bay.predict(x_test)
            print(confusion_matrix(y_test,predictions))
            print(classification_report(y_test,predictions))
            print(accuracy_score(y_test, predictions))
            crossValidate(bay, x_cv, y_cv) 
            print()

            print('Support Vector Machine:')
            svm=SVC(kernel='linear', gamma=0.1)
            svm.fit(x_train, y_train)
            predictions = svm.predict(x_test)
            print(confusion_matrix(y_test,predictions))
            print(classification_report(y_test,predictions))
            print(accuracy_score(y_test, predictions))
            crossValidate(svm, x_cv, y_cv) 
            print()

            print('Random Forest:')
            forest = RandomForestClassifier(n_estimators=100, random_state=0)
            forest.fit(x_train, y_train)
            predictions = forest.predict(x_test)
            print(confusion_matrix(y_test,predictions))
            print(classification_report(y_test,predictions))
            print(accuracy_score(y_test, predictions))
            crossValidate(forest, x_cv, y_cv) 
            print()

def crossValidate(model, x_cv, y_cv):
    cv_accuracy = cross_val_score(model, x_cv, y_cv, cv=5)
    print(f"Cross-Validation results: {cv_accuracy}")
    print(f"Mean Cross-Validation Accuracy: {np.mean(cv_accuracy)}")

if __name__ == "__main__":
    main()