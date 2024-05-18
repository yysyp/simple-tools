https://www.zhihu.com/question/358295896
由于工作需要想安装一个superset，对日常工作中的一些数据进行可视化监控，网上各种查询之后，终于安装成功。其间踩了不少坑，现将本人安装成功经验总结如下。希望对有同样需要的同学有点帮助。

anaconda prompt
conda create -n superset_env python=3.11
installed in: D:\app\anaconda3\envs

conda activate superset_env
pip install pillow
pip install wheel

vs_BuildTools.exe --> only need to install "desktop development with C++"
pip install python-geohash

pip install apache-superset


set FLASK_APP=superset
superset db upgrade
Refusing to start due to insecure SECRET_KEY 解决办法： 找到以上步骤中创建的虚拟环境在文件夹 ...conda\envs\superset_env\Lib 中添加superset_config.py 文件

（D:\app\anaconda3\envs\superset_env\Lib）
superset_config.py：

# Superset specific config
# SS 相关的配置
# 行数限制 5000 行
ROW_LIMIT = 5000

# 网站服务器端口 8088
SUPERSET_WEBSERVER_PORT = 8088

# Flask App Builder configuration
# Your App secret key will be used for securely signing the session cookie
# and encrypting sensitive information on the database
# Make sure you are changing this key for your deployment with a strong key.
# You can generate a strong key using `openssl rand -base64 42`
# Flask 应用构建器配置
# 应用密钥用来保护会话 cookie 的安全签名
# 并且用来加密数据库中的敏感信息
# 请确保在你的部署环境选择一个强密钥
# 可以使用命令 openssl rand -base64 42 来生成一个强密钥

SECRET_KEY = "ZT2uRVAMPKpVkHM/QA1QiQlMuUgAi7LLo160AHA99aihEjp03m1HR6Kg"

# The SQLAlchemy connection string to your database backend
# This connection defines the path to the database that stores your
# superset metadata (slices, connections, tables, dashboards, ...).
# Note that the connection information to connect to the datasources
# you want to explore are managed directly in the web UI
# SQLAlchemy 数据库连接信息
# 这个连接信息定义了 SS 元数据库的路径（切片、连接、表、数据面板等等）
# 注意：需要探索的数据源连接及数据库连接直接通过网页界面进行管理
#SQLALCHEMY_DATABASE_URI = 'sqlite:path/to/superset.db'

# Flask-WTF flag for CSRF
# 跨域请求攻击标识
WTF_CSRF_ENABLED = True

# Add endpoints that need to be exempt from CSRF protection
# CSRF 白名单
WTF_CSRF_EXEMPT_LIST = []

# A CSRF token that expires in 1 year
# CSFR 令牌过期时间 1 年
WTF_CSRF_TIME_LIMIT = 60 * 60 * 24 * 365

# Set this API key to enable Mapbox visualizations
# 接口密钥用来启用 Mapbox 可视化
MAPBOX_API_KEY = ''

-------------------------------------------------------------------------------------
superset db upgrade

superset fab create-admin
Username [admin]: admin/admin

superset init
superset load_examples

-----------------------------------------以上完成基础部署--------------------------------------------------------

运行每次运行superset 按照如下步骤
1、激活虚拟环境conda activate superset_env
2、设置FLASK_APP: set FLASK_APP=superset
3、运行superset: superset run -h 0.0.0.0 -p 8088 --with-threads --reload --debugger

作者：傅远柴
链接：https://www.zhihu.com/question/358295896/answer/3356063552
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

