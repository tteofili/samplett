# Looseen

playing with Lucene stuff

## Evaluation of Lucene Classifiers on 20 Newsgroups dataset

 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.5539516420064958
    * precision = 0.7096803632454434
    * recall = 0.6910000000000001
    * f1-measure = 0.7002156150263238
    * avgClassificationTime = 21.527
    * time = 43 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.5467909645034063
    * precision = 0.7029182263932785
    * recall = 0.6839999999999999
    * f1-measure = 0.6933300863791036
    * avgClassificationTime = 21.06
    * time = 42 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.5605348753162269
    * precision = 0.713289570716906
    * recall = 0.696
    * f1-measure = 0.7045387286395975
    * avgClassificationTime = 21.7515
    * time = 44 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.24644412191582002
    * precision = 0.5254341910678517
    * recall = 0.351
    * f1-measure = 0.42085852638886373
    * avgClassificationTime = 12.126
    * time = 24 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Dirichlet(2000.000000)} 
    * accuracy = 0.6885496183206107
    * precision = 0.8003351545026478
    * recall = 0.796
    * f1-measure = 0.7981616907792667
    * avgClassificationTime = 24.232
    * time = 49 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Jelinek-Mercer(0.300000)} 
    * accuracy = 0.7018014564967421
    * precision = 0.8075731115827314
    * recall = 0.8055
    * f1-measure = 0.8065352236162758
    * avgClassificationTime = 22.9355
    * time = 46 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.6927182615325963
    * precision = 0.8000344010996093
    * recall = 0.7984999999999999
    * f1-measure = 0.7992664641293894
    * avgClassificationTime = 21.395
    * time = 43 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR GB1} 
    * accuracy = 0.6855728968405025
    * precision = 0.7953990225129663
    * recall = 0.7934999999999998
    * f1-measure = 0.7944483764183171
    * avgClassificationTime = 24.2415
    * time = 49 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR PL3(800.0)} 
    * accuracy = 0.6880244088482075
    * precision = 0.7973816616538473
    * recall = 0.7955
    * f1-measure = 0.7964397194290511
    * avgClassificationTime = 24.659
    * time = 49 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB SPL-D} 
    * accuracy = 0.5643564356435643
    * precision = 0.746855442970171
    * recall = 0.6919999999999998
    * f1-measure = 0.7183820571557897
    * avgClassificationTime = 25.7235
    * time = 52 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB LL-L1} 
    * accuracy = 0.6989659134431252
    * precision = 0.8050873483909786
    * recall = 0.8035
    * f1-measure = 0.8042928910005583
    * avgClassificationTime = 23.029
    * time = 46 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.5775553967119371
    * precision = 0.7542890463687046
    * recall = 0.7035488559059989
    * f1-measure = 0.7280359424968937
    * avgClassificationTime = 174.93828399397893
    * time = 349 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.5619922288943836
    * precision = 0.7599851550937142
    * recall = 0.6889478458049887
    * f1-measure = 0.7227251158208516
    * avgClassificationTime = 175.24485699949824
    * time = 349 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.7064925086438725
    * precision = 0.8112987778465628
    * recall = 0.8083794063079776
    * f1-measure = 0.8098364610823404
    * avgClassificationTime = 175.38685398896138
    * time = 350 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.7159442724458205
    * precision = 0.8186181503507672
    * recall = 0.8158796124510411
    * f1-measure = 0.8172465872436411
    * avgClassificationTime = 175.46864024084294
    * time = 350 (sec)
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=1} 
    * accuracy = 0.6978886756238004
    * precision = 0.7698420844443173
    * recall = 0.7955448864013942
    * f1-measure = 0.7824824724143318
    * avgClassificationTime = 514.1289340101523
    * time = 1013 (sec)
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=2} 
    * accuracy = 0.7169291338582677
    * precision = 0.7879386875714776
    * recall = 0.8027613550707159
    * f1-measure = 0.795280960069477
    * avgClassificationTime = 729.0925925925926
    * time = 1418 (sec)
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=3} 
    * accuracy = 0.7232502011263073
    * precision = 0.793119715493429
    * recall = 0.803116551102504
    * f1-measure = 0.7980868294351592
    * avgClassificationTime = 932.9237199582027
    * time = 1786 (sec)
 
 * org.apache.lucene.classification.CachingNaiveBayesClassifier@3060fcaa 
    * accuracy = 0.6037390029325513
    * precision = 0.7464060413775376
    * recall = 0.706
    * f1-measure = 0.7256409711883911
    * avgClassificationTime = 32.8745
    * time = 66 (sec)
 
 * org.apache.lucene.classification.SimpleNaiveBayesClassifier@7046468b 
    * accuracy = 0.6027195883866225
    * precision = 0.7457491430343591
    * recall = 0.7052781422472144
    * f1-measure = 0.7249492487381611
    * avgClassificationTime = 224.9528349222278
    * time = 448 (sec)

 * MinHashClassifier{min=5, hashCount=1, hashSize=100} 
    * accuracy = 0.580658268996343
    * precision = 0.7146579324705653
    * recall = 0.7103056532493361
    * f1-measure = 0.7124751462569745
    * avgClassificationTime = 84.58585858585859
    * time = 263 (sec)
 
 * MinHashClassifier{min=10, hashCount=1, hashSize=100} 
    * accuracy = 0.7701277068295391
    * precision = 0.8479937273199326
    * recall = 0.845846737404352
    * f1-measure = 0.8469188716774162
    * avgClassificationTime = 145.19364833462433
    * time = 300 (sec)
 
 * MinHashClassifier{min=15, hashCount=1, hashSize=100} 
    * accuracy = 0.7238493723849372
    * precision = 0.7929020257905379
    * recall = 0.7960177294446539
    * f1-measure = 0.7944568228348643
    * avgClassificationTime = 202.7287405812702
    * time = 301 (sec)
 
 * MinHashClassifier{min=15, hashCount=3, hashSize=100} 
    * accuracy = 0.7031358885017421
    * precision = 0.7879206371526887
    * recall = 0.7796528692915371
    * f1-measure = 0.7837649501024745
    * avgClassificationTime = 487.62568008705114
    * time = 560 (sec)
 
 * MinHashClassifier{min=15, hashCount=3, hashSize=300} 
    * accuracy = 0.7031358885017421
    * precision = 0.7879206371526887
    * recall = 0.7796528692915371
    * f1-measure = 0.7837649501024745
    * avgClassificationTime = 488.0467899891186
    * time = 561 (sec)
 
 * MinHashClassifier{min=5, hashCount=3, hashSize=100} 
    * accuracy = 0.5823553217320923
    * precision = 0.7242290885618884
    * recall = 0.7088771466675443
    * f1-measure = 0.7164708898935142
    * avgClassificationTime = 200.7290612703766
    * time = 469 (sec)
    
    