# Looseen

playing with Lucene stuff

## Evaluation of Lucene Classifiers on 20 Newsgroups dataset

 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.4945364821995065
    * precision = 0.6615133008363202
    * recall = 0.6415
    * f1-measure = 0.6513529558203736
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.5467909645034063
    * precision = 0.7029182263932785
    * recall = 0.6839999999999999
    * f1-measure = 0.6933300863791036
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.5168738898756661
    * precision = 0.6757139405104384
    * recall = 0.66
    * f1-measure = 0.6677645373177179
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.22748267898383373
    * precision = 0.49613138727440936
    * recall = 0.33099999999999996
    * f1-measure = 0.39708199136045597
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Dirichlet(2000.000000)} 
    * accuracy = 0.6877862595419847
    * precision = 0.8001760960369415
    * recall = 0.7955000000000001
    * f1-measure = 0.7978311964167576
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Jelinek-Mercer(0.300000)} 
    * accuracy = 0.7011494252873564
    * precision = 0.8071961196514936
    * recall = 0.805
    * f1-measure = 0.8060965640581212
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.6920731707317073
    * precision = 0.7995131346812318
    * recall = 0.7979999999999999
    * f1-measure = 0.7987558507341249
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR GB1} 
    * accuracy = 0.6850513503233169
    * precision = 0.7949020679531459
    * recall = 0.7929999999999999
    * f1-measure = 0.7939498947808468
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR PL3(800.0)} 
    * accuracy = 0.6881433473122379
    * precision = 0.7979944628260195
    * recall = 0.7955
    * f1-measure = 0.7967452789917948
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB SPL-D} 
    * accuracy = 0.5645104277129728
    * precision = 0.7458481175304432
    * recall = 0.6919999999999998
    * f1-measure = 0.7179157395532617
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB LL-L1} 
    * accuracy = 0.6981999234009958
    * precision = 0.8045922217620506
    * recall = 0.8029999999999999
    * f1-measure = 0.8037953223818943
 
 * MinHashClassifier{min=5, hashCount=1, hashSize=100} 
    * accuracy = 0.5804878048780487
    * precision = 0.7144443817037239
    * recall = 0.7101097284844459
    * f1-measure = 0.7122704603222902
 
 * MinHashClassifier{min=10, hashCount=1, hashSize=100} 
    * accuracy = 0.7702175125488009
    * precision = 0.8489089132229746
    * recall = 0.8460719939891741
    * f1-measure = 0.8474880795053676
 
 * MinHashClassifier{min=15, hashCount=1, hashSize=100} 
    * accuracy = 0.7223001402524544
    * precision = 0.7919621587367596
    * recall = 0.7949900391725502
    * f1-measure = 0.7934732103799563
 
 * MinHashClassifier{min=15, hashCount=3, hashSize=100} 
    * accuracy = 0.69901547116737
    * precision = 0.7842666420409727
    * recall = 0.7756747797251047
    * f1-measure = 0.7799470497067199
 
 * MinHashClassifier{min=15, hashCount=3, hashSize=300} 
    * accuracy = 0.6992269852424455
    * precision = 0.7843948471691778
    * recall = 0.7758029848533099
    * f1-measure = 0.780075258723514
 
 * MinHashClassifier{min=5, hashCount=3, hashSize=100} 
    * accuracy = 0.5822113423092615
    * precision = 0.7235829351585766
    * recall = 0.7083199522910384
    * f1-measure = 0.715870097898894
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.6783804430863254
    * precision = 0.7919924728473279
    * recall = 0.788271464912113
    * f1-measure = 0.7901275879976023
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.6991150442477876
    * precision = 0.8090523801719536
    * recall = 0.8034246323959138
    * f1-measure = 0.806228685509813
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.7054741711642252
    * precision = 0.8106042784087719
    * recall = 0.8076796763925813
    * f1-measure = 0.8091393346948251
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.7151726814124951
    * precision = 0.8172444696545258
    * recall = 0.815273210619768
    * f1-measure = 0.8162576499931659
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=1} 
    * accuracy = 0.7030943987465726
    * precision = 0.7706789722476249
    * recall = 0.7986970884763802
    * f1-measure = 0.7844379262420743
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=2} 
    * accuracy = 0.7316875758802105
    * precision = 0.793574371777295
    * recall = 0.8059233472679803
    * f1-measure = 0.7997011891840514
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=3} 
    * accuracy = 0.7676659528907923
    * precision = 0.8253791919529374
    * recall = 0.8244044567808525
    * f1-measure = 0.8248915364174246
 
 * org.apache.lucene.classification.CachingNaiveBayesClassifier@3e9ad1bb 
    * accuracy = 0.6035936927026035
    * precision = 0.7462251923388931
    * recall = 0.7058232323232323
    * f1-measure = 0.7254621379727343
 
 * org.apache.lucene.classification.SimpleNaiveBayesClassifier@167ce3df 
    * accuracy = 0.6016504126031508
    * precision = 0.7434419793832153
    * recall = 0.7051578525678713
    * f1-measure = 0.7237940225142555
