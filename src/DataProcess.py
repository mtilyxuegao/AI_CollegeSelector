import pandas as pd
import re


def clean_excel_data(filename, sheet_name, columns, default_values):
    # 读取Excel文件
    data_frame = pd.read_excel(filename, sheet_name=sheet_name)

    cleaned_data = []

    # 遍历每一行
    for index, row in data_frame.iterrows():
        cleaned_row = []

        # 遍历特定列
        for column in columns:
            # 提取列的值，并将其转换为字符串类型
            value = str(row[column])

            # 处理缺失值
            if pd.isnull(value):
                # 如果提供了默认值，则使用默认值替换缺失值
                if default_values and column in default_values:
                    value = default_values[column]
                else:
                    # 如果没有提供默认值，则将缺失值替换为 None
                    value = None
            else:
                # 对第三列数据进行范围提取
                # 处理格式为"数字1||数字2||数字3||数字4"的数据
                if "||" in str(value):
                    value_parts = str(value).split("||")
                    value = value_parts[0].strip()
                else:
                    value = str(value)
                if column == '本科GPA':
                    match = re.search(r'(\d+\.\d+)', value)
                else:
                    match = re.search(r'(\d+)', value)

                if match:
                    value = match.group(0)

            cleaned_row.append(value)

        cleaned_data.append(cleaned_row)

    return cleaned_data


# 指定Excel文件名、工作表名称和特定列
filename = '001 16-22 美国计算机科学.xlsx'
sheet_name = 'Sheet1'
columns = ['本科GPA', 'TOEFL', 'GRE']

# 指定缺失值的默认值（可选）
default_values = {'本科GPA': '3.0', 'TOEFL': '100','GRE':'320'}

# 调用数据清洗函数
cleaned_data = clean_excel_data(filename, sheet_name, columns, default_values)

# 打印清洗后的数据
for row in cleaned_data:
    print(row)
