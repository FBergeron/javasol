echo "Enter release (e.g.: 1.4): "
read rel
echo $rel

cp -r dist/javasol javasol-$rel
tar zcf javasol-$rel.tar.gz javasol-$rel
rm -rf javasol-$rel
exit 0
