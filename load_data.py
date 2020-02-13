import os
import pickle

import nltk

def main():
    files_pos = os.listdir('txt_sentoken/pos')
    files_pos = [open('txt_sentoken/pos/'+f, 'r').read() for f in files_pos]
    files_neg = os.listdir('txt_sentoken/neg')
    files_neg = [open('txt_sentoken/neg/'+f, 'r').read() for f in files_neg]

    #downloads
    nltk.download('stopwords')
    nltk.download('averaged_perceptron_tagger')
    nltk.download('punkt')

    docs = []
    
    # All unigrams
    # positive
    for x in files_pos:
        docs.append((x,1))
    # negative    
    for x in files_neg:
        docs.append((x,0))

    out = open('data/feat1.pkl', 'wb')
    pickle.dump(docs, out)
    out.close()

    docs = []
    # All nouns, verbs, adjectives and
    # positive
    for x in files_pos:
        out = []
        tagged = nltk.pos_tag(x.split())
        for t in tagged:
            if t[1][0] in ['R','N','V','J']:
                out.append(t[0])
        docs.append((" ".join(out),1))
    # negative    
    for x in files_neg:
        out = []
        tagged = nltk.pos_tag(x.split())
        for t in tagged:
            if t[1][0] in ['R','N','V','J']:
                out.append(t[0])
        docs.append((" ".join(out),0))

    out = open('data/feat2.pkl', 'wb')
    pickle.dump(docs, out)
    out.close()

if __name__ == "__main__":
    main()