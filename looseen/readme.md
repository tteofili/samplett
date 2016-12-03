# Looseen

playing with Lucene stuff

## Evaluation of Lucene Classifiers on 20 Newsgroups dataset

 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.4945364821995065
    * precision = 0.6615133008363202
    * recall = 0.6415
    * f1-measure = 0.6513529558203736
    * avgClassificationTime = 27.39
    * time = 110 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.5467909645034063
    * precision = 0.7029182263932785
    * recall = 0.6839999999999999
    * f1-measure = 0.6933300863791036
    * avgClassificationTime = 26.941
    * time = 109 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.5165303945965162
    * precision = 0.6773731713165202
    * recall = 0.66
    * f1-measure = 0.6685737424040111
    * avgClassificationTime = 27.447
    * time = 110 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=F1EXP} 
    * accuracy = 0.5417867435158501
    * precision = 0.6983143852880398
    * recall = 0.6820000000000002
    * f1-measure = 0.6900607801273632
    * avgClassificationTime = 40.849
    * time = 137 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=F1LOG} 
    * accuracy = 0.5469534050179211
    * precision = 0.6993125896986854
    * recall = 0.6839999999999999
    * f1-measure = 0.6915715434326974
    * avgClassificationTime = 36.7255
    * time = 129 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Dirichlet(2000.000000)} 
    * accuracy = 0.6885496183206107
    * precision = 0.8003351545026478
    * recall = 0.796
    * f1-measure = 0.7981616907792667
    * avgClassificationTime = 28.0165
    * time = 111 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Jelinek-Mercer(0.300000)} 
    * accuracy = 0.7018014564967421
    * precision = 0.8075731115827314
    * recall = 0.8055
    * f1-measure = 0.8065352236162758
    * avgClassificationTime = 26.628
    * time = 109 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.6933638443935927
    * precision = 0.8004833931769151
    * recall = 0.7989999999999999
    * f1-measure = 0.7997410087240737
    * avgClassificationTime = 24.026
    * time = 103 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR GB1} 
    * accuracy = 0.6855728968405025
    * precision = 0.7954320729532244
    * recall = 0.7934999999999998
    * f1-measure = 0.794464861817871
    * avgClassificationTime = 28.844
    * time = 113 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR PL3(800.0)} 
    * accuracy = 0.6880244088482075
    * precision = 0.7973816616538473
    * recall = 0.7955
    * f1-measure = 0.7964397194290511
    * avgClassificationTime = 29.1
    * time = 113 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB SPL-D} 
    * accuracy = 0.5643564356435643
    * precision = 0.7470108015858785
    * recall = 0.6919999999999998
    * f1-measure = 0.7184539186609824
    * avgClassificationTime = 31.1865
    * time = 118 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB LL-L1} 
    * accuracy = 0.6996168582375479
    * precision = 0.8056275448559236
    * recall = 0.8039999999999999
    * f1-measure = 0.8048129495971564
    * avgClassificationTime = 27.0245
    * time = 109 (sec)
 
 * MinHashClassifier{min=15, hashCount=1, hashSize=100} 
    * accuracy = 0.723463687150838
    * precision = 0.7925230334378612
    * recall = 0.795681683484373
    * f1-measure = 0.7940992174711647
    * avgClassificationTime = 308.46817691477884
    * time = 341 (sec)
 
 * MinHashClassifier{min=30, hashCount=3, hashSize=300} 
    * accuracy = 0.6991701244813278
    * precision = 0.7244442368033682
    * recall = 0.7232470635552394
    * f1-measure = 0.7238451551760577
    * avgClassificationTime = 934.8204081632653
    * time = 513 (sec)
 
 * MinHashClassifier{min=10, hashCount=1, hashSize=100} 
    * accuracy = 0.7709838799332963
    * precision = 0.8492860877394117
    * recall = 0.8465246411289309
    * f1-measure = 0.8479031160737699
    * avgClassificationTime = 222.35686578743213
    * time = 342 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=LM Jelinek-Mercer(0.300000)} 
    * accuracy = 0.5187607573149742
    * precision = 0.7329905695421368
    * recall = 0.6492894248608535
    * f1-measure = 0.6886058211845795
    * avgClassificationTime = 150.1881585549423
    * time = 355 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=IB LL-L1} 
    * accuracy = 0.5280665280665281
    * precision = 0.7346825328324595
    * recall = 0.6583300350443209
    * f1-measure = 0.6944138032052934
    * avgClassificationTime = 150.33617661816356
    * time = 355 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.6752851711026616
    * precision = 0.7894131101641592
    * recall = 0.7858694083694082
    * f1-measure = 0.7876372733714722
    * avgClassificationTime = 145.0551931761164
    * time = 344 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.6993464052287581
    * precision = 0.8084578403707496
    * recall = 0.8039915481344053
    * f1-measure = 0.8062185087045278
    * avgClassificationTime = 145.64224786753638
    * time = 346 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.6887189292543021
    * precision = 0.7975274982047024
    * recall = 0.795797876726448
    * f1-measure = 0.7966617486772696
    * avgClassificationTime = 145.8344204716508
    * time = 346 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.7021931512120047
    * precision = 0.808062361470481
    * recall = 0.8058540507111935
    * f1-measure = 0.806956695282603
    * avgClassificationTime = 146.27145007526343
    * time = 347 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=F1EXP} 
    * accuracy = 0.6099554234769688
    * precision = 0.7438509915231809
    * recall = 0.7363708513708513
    * f1-measure = 0.7400920214095092
    * avgClassificationTime = 159.07225288509784
    * time = 372 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=F1LOG} 
    * accuracy = 0.6099554234769688
    * precision = 0.7438509915231809
    * recall = 0.7363708513708513
    * f1-measure = 0.7400920214095092
    * avgClassificationTime = 155.9613647767185
    * time = 366 (sec)
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=1} 
    * accuracy = 0.6996939556235654
    * precision = 0.7710115896176369
    * recall = 0.79660511763127
    * f1-measure = 0.7835994286132706
    * avgClassificationTime = 384.97271349166243
    * time = 817 (sec)
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=2} 
    * accuracy = 0.7162899454403742
    * precision = 0.7881340983006455
    * recall = 0.8022026689190964
    * f1-measure = 0.7951061563246424
    * avgClassificationTime = 464.44235652615544
    * time = 970 (sec)
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=3} 
    * accuracy = 0.722397476340694
    * precision = 0.7934184610630093
    * recall = 0.8032573070645604
    * f1-measure = 0.7983075701789704
    * avgClassificationTime = 524.8223583460949
    * time = 1084 (sec)
 
 * org.apache.lucene.classification.CachingNaiveBayesClassifier@6975cad2 
    * accuracy = 0.6035936927026035
    * precision = 0.7462251923388931
    * recall = 0.7058232323232323
    * f1-measure = 0.7254621379727343
    * avgClassificationTime = 31.55927963981991
    * time = 118 (sec)
 
 * org.apache.lucene.classification.SimpleNaiveBayesClassifier@576eb503 
    * accuracy = 0.6036069193963931
    * precision = 0.745871438373861
    * recall = 0.7060443655723159
    * f1-measure = 0.7254116596484205
    * avgClassificationTime = 221.81005025125629
    * time = 497 (sec)
