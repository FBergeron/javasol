echo "Enter release (e.g.: 1.4): "
read rel
echo $rel

mkdir javasol-$rel
cp dist/javasol/index.html javasol-$rel
mkdir javasol-$rel/jar
mkdir javasol-$rel/img
cp dist/javasol/jar/*.jar javasol-$rel/jar
cp dist/javasol/img/*.* javasol-$rel/img
tar -cf javasol-$rel.tar javasol-$rel
gzip javasol-$rel.tar
exit 0
