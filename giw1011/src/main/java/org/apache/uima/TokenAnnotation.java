

/* First created by JCasGen Wed Apr 27 15:13:03 CEST 2011 */
package org.apache.uima;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;


/** Single token annotation
 * Updated by JCasGen Wed Apr 27 15:13:03 CEST 2011
 * XML source: /Users/tommasoteofili/Documents/workspaces/github/samplett/giw1011/src/main/resources/AggregateGiw1011.xml
 * @generated */
public class TokenAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(TokenAnnotation.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected TokenAnnotation() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public TokenAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public TokenAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public TokenAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: stem

  /** getter for stem - gets stem
   * @generated */
  public String getStem() {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_stem == null)
      jcasType.jcas.throwFeatMissing("stem", "org.apache.uima.TokenAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_stem);}
    
  /** setter for stem - sets stem 
   * @generated */
  public void setStem(String v) {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_stem == null)
      jcasType.jcas.throwFeatMissing("stem", "org.apache.uima.TokenAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_stem, v);}    
   
    
  //*--------------*
  //* Feature: posTag

  /** getter for posTag - gets part of speech tag
   * @generated */
  public String getPosTag() {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "org.apache.uima.TokenAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_posTag);}
    
  /** setter for posTag - sets part of speech tag 
   * @generated */
  public void setPosTag(String v) {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "org.apache.uima.TokenAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_posTag, v);}    
   
    
  //*--------------*
  //* Feature: tokenType

  /** getter for tokenType - gets token type
   * @generated */
  public String getTokenType() {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_tokenType == null)
      jcasType.jcas.throwFeatMissing("tokenType", "org.apache.uima.TokenAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_tokenType);}
    
  /** setter for tokenType - sets token type 
   * @generated */
  public void setTokenType(String v) {
    if (TokenAnnotation_Type.featOkTst && ((TokenAnnotation_Type)jcasType).casFeat_tokenType == null)
      jcasType.jcas.throwFeatMissing("tokenType", "org.apache.uima.TokenAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TokenAnnotation_Type)jcasType).casFeatCode_tokenType, v);}    
  }

    