#for i in `find scripts/scripts/npc -name "*.js"`; do ./gbk2u8.sh "$i";  done
for i in scripts/scripts/npc/*副本*.js;
do
  echo  "processing file: $i."

  file "$i"  | grep UTF-8 >/dev/null 2>/dev/null

  if [ $? -eq 0 ] ; then
    echo "Already is UTF-8, NO need to conversion"
  else
    fn=`basename "$i"`
    iconv -f gbk -t utf-8 "$i" > "/tmp/$fn.utf8"
    mv "/tmp/$fn.utf8" "$i"
    echo "Convert done $fn"

  fi
done





