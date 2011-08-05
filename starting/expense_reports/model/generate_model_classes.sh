model_dir="org/springsource/examples/expenses/model/"
guaranteeDirectory(){ mkdir -p $1; }
mvn clean install hibernate3:hbm2java ; 
python update_model_classes.py ;
export d="../services-engine/src/main/java/$model_dir"
guaranteeDirectory "$d"
cp target/hibernate3/generated-sources/$model_dir*  $d;
