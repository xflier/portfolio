import pandas as pd

inputfile = "dictionaries/institution_directory_2023.xlsx"
outputfile = "dictionaries/institution_directory_2023.txt"

df = pd.read_excel(inputfile, sheet_name="varlist")
df = df[df.notna()]
print(df.head(5))

df_size = df.shape[0]
print(df_size)

outfile = open(outputfile, 'w')
outfile.write("Instition table should have the following fields: \n")
outfile.write("\n")
outfile.close()

outfile = open(outputfile, 'a')

for index, row in df.iterrows(): 
    colname = row["varname"]
    coltype = row["DataType"]
    colsize = row["Fieldwidth"]
    flen = 0 
    if colsize != "" or colsize == None : flen = int(colsize)

    stmt = colname

    match (coltype, flen):
        case (coltype, flen) if coltype == 'N' and flen > 0 and flen < 4:
            stmt = f"{stmt}  SMALLINT"
        case (coltype, flen) if coltype == 'N' and flen > 0 and flen < 6:
            stmt = f"{stmt}  INTEGER"
        case (coltype, flen) if coltype == 'N' :
            stmt = f"{stmt}  BIGINT"
        case (coltype, flen) if coltype == 'A' and flen > 0 and flen < 4000:
            stmt = f"{stmt}  VARCHAR({flen})"
        case _ :
            stmt = f"{stmt}  VARCHAR"
    
    if (index != df_size-1) : stmt = f"{stmt},"

    outfile.write(stmt)
    if (index != df_size -1 ) : outfile.write("\n")

outfile.close()