#!/bin/sh
echo Enter latest revision number.
read rev
mkdir javasol-r$rev-svn-backup
rsync -av javasol.svn.sourceforge.net::svn/javasol/* javasol-r$rev-svn-backup
tar zcf javasol-r$rev-svn-backup.tar.gz javasol-r$rev-svn-backup
rm -rf javasol-r$rev-svn-backup
